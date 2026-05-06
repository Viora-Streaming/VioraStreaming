import type {Account, UpdateAccountRequest} from "../types/accountTypes.ts";
import {apiFetch} from "../utils/apiUtils.ts";
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