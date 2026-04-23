import {RouterProvider} from "react-router-dom";
import type {createBrowserRouter} from "react-router";

type AppLoaderProps = {
  router: ReturnType<typeof createBrowserRouter>;
}

export function AppLoader({router}: AppLoaderProps) {
  return <>
    <RouterProvider router={router}/>
  </>
}