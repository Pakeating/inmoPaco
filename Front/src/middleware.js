import { defineMiddleware } from "astro:middleware";

//WIP, lo usare para comprobar las peticiones que se mandan al servidor.
export const onRequest = defineMiddleware(async (context, next) => {
  console.log(`[Middleware] Interceptando solicitud a: ${context.url.pathname}`);
  
  const response = await next();
  
  return response;
});
