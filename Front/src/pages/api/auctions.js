export const prerender = false;

/**
 * Endpoint de la API: /api/auctions
 * actúa como un proxy para reenviar las peticiones de búsqueda al backend Java.
 */
export async function POST ({ request }) {
  let body;
  try {
    body = await request.json();
  } catch (e) {
    console.error('[API Proxy] Error parsing request body:', e);
    return new Response(JSON.stringify({ error: 'Invalid JSON body' }), {
      status: 400,
      headers: { "Content-Type": "application/json" }
    });
  }

  console.log('[API Proxy] Forwarding body to backend:', body);

  const url = new URL(request.url);
  const queryParams = url.searchParams.toString();
  const backendUrl = `http://localhost:8083/search${queryParams ? '?' + queryParams : ''}`;

  try {
    const response = await fetch(backendUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(body)
    });

    if (!response.ok) {
      console.error('[API Proxy] Backend returned error:', response.status);
      return new Response(JSON.stringify({ error: 'Backend error' }), {
        status: response.status,
        headers: { "Content-Type": "application/json" }
      });
    }

    const data = await response.json();
    
    // Devolvemos el objeto Page de Spring tal cual.
    return new Response(JSON.stringify(data), {
      status: 200,
      headers: { "Content-Type": "application/json" }
    });
  } catch (error) {
    console.error('[API Proxy] Fetch error:', error);
    return new Response(JSON.stringify({ error: 'Connection failed' }), {
      status: 500,
      headers: { "Content-Type": "application/json" }
    });
  }
};
