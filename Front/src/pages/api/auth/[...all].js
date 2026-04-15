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
  const db = context.locals.runtime?.env?.DB;

  if (!db) {
    console.error(
      "[Auth API] D1 Binding not available"
    );
    return new Response(
      JSON.stringify({ error: " Auth not available" }),
      {
        status: 503,
        headers: { "Content-Type": "application/json" },
      }
    );
  }

  const auth = getAuth(db);
  return auth.handler(context.request);
};
