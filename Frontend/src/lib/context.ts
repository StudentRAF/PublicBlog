import * as React from "react";
import { Page, UserData, UserToken } from "@/lib/types.ts";

export type ApplicationData = {
  page: Page,
  user?: UserData,
  authorization?: UserToken,
  path?: string | number,
}

export type ApplicationContextData = {
  setData: React.Dispatch<React.SetStateAction<ApplicationData>>,
  data: ApplicationData,
}

export const defaultAppData : ApplicationData = {
  page: "posts"
}

export const ApplicationContext = React.createContext<ApplicationContextData>({
  setData: () => {},
  data: defaultAppData,
});
