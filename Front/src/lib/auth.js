import { betterAuth } from "better-auth";

/**
 * recibe el binding y crea la instancia de BetterAuth con el binding D1 de cloudfalre.
 *
 * NO usar como singleton global, debe llamarse UNA VEZ POR REQUEST, pasando el env.DB.
 * El contexto de los workers es request-scoped.
 */
export const getAuth = (db) => {
  return betterAuth({
    // secret para firma de tokens
    secret: import.meta.env.BETTER_AUTH_SECRET,

    // binding D1
    database: db,

    //activada la autenticación por email y contraseña
    emailAndPassword: {
      enabled: true,
    },

    // campo custom "role" en betterAuth
    user: {
      additionalFields: {
        role: {
          type: "string",
          required: false,
          defaultValue: "user",
        },
      },
    },
  });
};
