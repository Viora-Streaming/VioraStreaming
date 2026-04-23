import type {Router as RemixRouter} from '@remix-run/router/dist/router';
import {RouterProvider} from "react-router-dom";

type AppLoaderProps = {
  router: RemixRouter;
}

export function AppLoader({router}: AppLoaderProps) {
  return <>
    <RouterProvider router={router}/>
  </>
}