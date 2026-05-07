export const API_BASE = import.meta.env.VITE_API_URL ?? 'http://localhost:8080'

const tokenName = "JWT_TOKEN";

export const getToken = () => localStorage.getItem(tokenName);

export const removeToken = () => localStorage.removeItem(tokenName);

export class ApiError extends Error {
  public status: number;

  constructor(status: number, message: string) {
    super(message);
    this.status = status;
  }
}

async function makeAuthenticatedRequest(url: string, options?: RequestInit) {
  const fullUrl = `${API_BASE}${url}`;
  const token = getToken();

  const existingHeaders = options?.headers instanceof Headers
      ? Object.fromEntries(options.headers.entries())
      : (options?.headers as Record<string, string> ?? {});

  const res = await fetch(fullUrl, {
    ...options,
    headers: {
      ...existingHeaders,
      ...(token ? {Authorization: `Bearer ${token}`} : {}),
    },
  });

  if (!res.ok) {
    const body = await res.json().catch(() => ({}));
    throw new ApiError(res.status, body.message ?? "Request failed");
  }

  return res;
}

export async function apiFetch(url: string, options?: RequestInit) {
  const res = await makeAuthenticatedRequest(url, options);
  return res.json();
}

export async function apiPostWithoutResult(url: string, options?: RequestInit) {
  await makeAuthenticatedRequest(url, options);
}

export function makeQueryFromProps<T extends Record<string, any>>(params: T): string {
  const query = new URLSearchParams();

  function flatten(obj: Record<string, any>, prefix = "") {
    Object.entries(obj).forEach(([key, value]) => {
      if (value === undefined || value === null) return;
      const fullKey = prefix ? `${prefix}.${key}` : key;
      if (typeof value === "object" && !Array.isArray(value)) {
        flatten(value, fullKey);
      } else {
        query.append(fullKey, String(value));
      }
    });
  }

  flatten(params);
  return query.toString();
}