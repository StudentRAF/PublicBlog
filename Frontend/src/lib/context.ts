import * as React from "react";
import { Page, UserData } from "@/lib/types.ts";

export type ApplicationData = {
  page: Page,
  user?: UserData,
  path?: string | number,
}

export type ApplicationContextData = {
  setData: React.Dispatch<React.SetStateAction<ApplicationData>>,
  data: ApplicationData,
}

export const defaultAppData : ApplicationData = {
  page: "login"
}

export const ApplicationContext = React.createContext<ApplicationContextData>({
  setData: () => {},
  data: defaultAppData,
});
