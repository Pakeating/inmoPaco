import { defineMiddleware } from "astro:middleware";
import { getAuth } from "./lib/auth";
import { SignJWT } from "jose";

export const onRequest = defineMiddleware(async (context, next) => {
  const db = context.locals.runtime?.env?.DB;

  if (db) {
    try {
      const auth = getAuth(db);

      // sesion activa a partir de las cookies
      const session = await auth.api.getSession({
        headers: context.request.headers,
      });

      let jwtPayload = {
        sub: "anonymous",
        name: "Invitado",
        role: "guest",
        email: "guest@inmopaco.com"
      };

      if (session) {
        // guarda usuario y sesión en locals
        context.locals.user = session.user;
        context.locals.session = session.session;

        jwtPayload = {
          sub: session.user.id,
          name: session.user.name,
          role: session.user.role ?? "user",
          email: session.user.email,
        };
      }

      // generamos JWT siempre, con datos de user o guest
      const secret = new TextEncoder().encode(import.meta.env.JWT_SECRET);
      const jwtToken = await new SignJWT(jwtPayload)
        .setProtectedHeader({ alg: "HS256" })
        .setIssuedAt()
        .setExpirationTime("15m")
        .sign(secret);

      context.locals.jwtToken = jwtToken;

      const url = new URL(context.request.url);
      
      // si se accede a administration y no es admin se redirige
      if (url.pathname.startsWith("/administration")) {
        
        if (!session || session.user.role !== "admin") {
          console.warn(`[Middleware] Access bloqued to ${url.pathname} for ${session?.user?.email ?? "Anon"}`);
          return context.redirect("/login");
        }
      }

    } catch (err) {
      // sesion erronea o expired
      console.error("[Middleware] Session error:", err?.message);
    }
  }

  return next();
});
