const API_BASE = import.meta.env.VITE_API_URL ?? 'http://localhost:8080'

export class ApiError extends Error {
  public status: number;
  constructor(status: number, message: string) {
    super(message);
    this.status = status;
  }
}

export async function apiFetch(url: string, options?: RequestInit) {
  const fullUrl = `${API_BASE}${url}`;
  const res = await fetch(fullUrl, options);

  if (!res.ok) {
    const body = await res.json().catch(() => ({}));
    throw new ApiError(res.status, body.message ?? "Request failed");
  }

  return res.json();
}

export async function apiPostWithoutResult(url: string, options?: RequestInit) {
  const fullUrl = `${API_BASE}${url}`;
  const res = await fetch(fullUrl, options);

  if (!res.ok) {
    const body = await res.json().catch(() => ({}));
    throw new ApiError(res.status, body.message ?? "Request failed");
  }
}