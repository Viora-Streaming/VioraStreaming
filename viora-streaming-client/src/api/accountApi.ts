import type {Account, UpdateAccountRequest} from "../types/accountTypes.ts";
import {apiFetch, apiPostWithoutResult} from "../utils/apiUtils.ts";
import {API_PATHS} from "../constants/apiConstants.ts";


export async function updateAccount(request: UpdateAccountRequest): Promise<Account> {
  return apiFetch(API_PATHS.account, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(request),
  });
}

export async function getAccount(): Promise<Account> {
  return apiFetch(API_PATHS.account);
}

export async function deleteAccount(): Promise<void> {
  return apiPostWithoutResult(API_PATHS.account, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    }
  })
}

export async function sendResetPasswordLink(email: string): Promise<void> {
  const api = `${API_PATHS.dropPassword}?email=${email}`;
  return apiPostWithoutResult(api);
}

export async function updatePassword(password: string): Promise<void> {
  apiPostWithoutResult("/api/v1/accounts/drop-password", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
    body: JSON.stringify({password: password}),
  });
}