import {apiFetch, apiPostWithoutResult} from "../utils/apiUtils.ts";
import {API_PATHS} from "../constants/apiConstants.ts";

export interface LoginPayload {
  email: string;
  password: string;
}

export interface RegisterPayload {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export async function loginRequest(
    payload: LoginPayload
): Promise<LoginResponse> {
  return apiFetch(API_PATHS.login, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });
}

export async function registerRequest(
    payload: RegisterPayload
): Promise<void> {
  await apiPostWithoutResult(API_PATHS.register, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });
}


