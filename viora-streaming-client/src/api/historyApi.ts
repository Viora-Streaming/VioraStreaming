import {apiFetch} from "../utils/apiUtils.ts";
import {API_PATHS} from "../constants/apiConstants.ts";
import type {History} from "../types/historyTypes.ts";

export async function getUserHistories(): Promise<History[]> {
  return apiFetch(API_PATHS.history)
}