export const prerender = false;

const BACKEND_BASE_URL = import.meta.env.BACKEND_BASE_URL;

export async function POST ({ request, locals }) {
  const env = locals.runtime?.env;
  const backendBase = env?.BACKEND_BASE_URL || import.meta.env.BACKEND_BASE_URL;

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

  const url = new URL(request.url);
  const queryParams = url.searchParams.toString();
  
  const backendUrl = `${backendBase}/bff/properties${queryParams ? '?' + queryParams : ''}`;

  try {
    const backendHeaders = { 
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${locals.jwtToken}`
    };

    const response = await fetch(backendUrl, {
      method: 'POST',
      headers: backendHeaders,
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

export async function GET ({ request, locals }) {
  const env = locals.runtime?.env;
  const backendBase = env?.BACKEND_BASE_URL || import.meta.env.BACKEND_BASE_URL;

  const url = new URL(request.url);
  const idValue = url.searchParams.get('id');
  const city = url.searchParams.get('city');
  const province = url.searchParams.get('province');
  const contractType = url.searchParams.get('contractType');
  const propertyType = url.searchParams.get('propertyType');
  
  const page = url.searchParams.get('page') || '0';
  const size = url.searchParams.get('size') || '10';
  
  let backendUrl;
  const queryParams = new URLSearchParams({ page, size }).toString();

  if (idValue) {
    backendUrl = `${backendBase}/bff/properties/${idValue}?${queryParams}`;
  } else if (city) {
    backendUrl = `${backendBase}/bff/properties/city/${city}?${queryParams}`;
  } else if (province) {
    backendUrl = `${backendBase}/bff/properties/province/${province}?${queryParams}`;
  } else if (contractType) {
    backendUrl = `${backendBase}/bff/properties/contract/${contractType}?${queryParams}`;
  } else if (propertyType) {
    backendUrl = `${backendBase}/bff/properties/type/${propertyType}?${queryParams}`;
  } else {
    backendUrl = `${backendBase}/bff/properties?${queryParams}`;
  }

  try {
    const backendHeaders = { 
      'Accept': 'application/json',
      'Authorization': `Bearer ${locals.jwtToken}`
    };

    const response = await fetch(backendUrl, {
      method: 'GET',
      headers: backendHeaders
    });

    if (!response.ok) {
      console.error('[API Proxy] Backend returned error:', response.status);
      return new Response(JSON.stringify({ error: 'Backend error' }), {
        status: response.status,
        headers: { "Content-Type": "application/json" }
      });
    }

    const data = await response.json();
    
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
}