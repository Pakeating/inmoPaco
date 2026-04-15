/**
 * recibe el binding D1 y datos opcionales de entorno para crear la instancia de BetterAuth.
 *
 * NO usar como singleton global, debe llamarse UNA VEZ POR REQUEST, pasando el env.DB.
 * El contexto de los workers es request-scoped.
 */
export const getAuth = (db, options = {}) => {
  return betterAuth({
    // Prioridad: 1 opciones inyectadas, 2 variable de entorno, 3 nada (fallará si no hay secreto)
    secret: options.secret || import.meta.env.BETTER_AUTH_SECRET,

    // URL base: necesaria para redirecciones y cookies seguras en Cloudflare
    baseURL: options.baseURL || import.meta.env.BETTER_AUTH_URL,

    database: db,

    emailAndPassword: {
      enabled: true,
    },

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
