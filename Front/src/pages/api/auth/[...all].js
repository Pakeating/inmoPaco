import { getAuth } from "../../../lib/auth";

export const prerender = false;

/**
 * Catch-all handler para las peticiones de betterAuth en /api/auth/*
 *
 * gestiona las distintas llamadas de betterAuth:
 *  - POST /api/auth/sign-in/email -> Login
 *  - POST /api/auth/sign-up/email -> Registro
 *  - POST /api/auth/sign-out      -> Logout
 *  - GET  /api/auth/get-session   -> Sesión activa
 *  - GET  /api/auth/session       -> Sesión activa (alias)
 *  - Otros
 */
export const ALL = async (context) => {
  const env = context.locals.runtime?.env;
  const db = env?.DB;

  if (!db) {
    console.error("[Auth API] D1 Binding not available");
    return new Response(JSON.stringify({ error: " Auth not available" }), {
      status: 503,
      headers: { "Content-Type": "application/json" },
    });
  }

  // Detectamos baseURL dinámicamente si no está en env
  const baseURL = env?.BETTER_AUTH_URL || context.url.origin;

  const auth = getAuth(db, {
    secret: env?.BETTER_AUTH_SECRET,
    baseURL: baseURL
  });

  return auth.handler(context.request);
};
