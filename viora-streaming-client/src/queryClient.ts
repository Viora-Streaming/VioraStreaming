import { QueryClient } from "@tanstack/react-query";
import {store} from "./store/store.ts";
import {openModal} from "./store/modals.ts";
import {ModalTypes} from "./types/modalTypes.ts";

export const queryClient = new QueryClient({
  defaultOptions: {
    mutations: {
      onError(error: Error) {
        if (isServerError(error)) {
          store.dispatch(
              openModal({
                data: { message: "Something went wrong. Please try again." },
                type: ModalTypes.networkErrorModal,
                id: "network-error",
              })
          );
        }
      },
    },
    queries: {

    },
  },
});

function isServerError(error: unknown): boolean {
  if (error instanceof TypeError && error.message === "Failed to fetch") {
    return true;
  }

  return (
      error instanceof Error &&
      "status" in error &&
      typeof (error as any).status === "number" &&
      (error as any).status >= 500
  );
}