import NavigationButton from "@/components/common/NavigationButton.tsx";
import { useContext } from "react";
import { ApplicationContext } from "@/lib/context.ts";
import { Page } from "@/lib/types.ts";
import { Config } from "@/lib/config.ts";

const Header = () => {
  const appContext = useContext(ApplicationContext);

  const navigate = (page : Page) => {
    appContext.setData(prevData => ({...prevData, page: page}))
  }

  return(
    <div className="flex gap-4 h-12 mt-0.5 border-b border-gray-900 px-64 items-center">
      <img
        className="h-8 w-8 mr-2 cursor-pointer"
        src={`${Config.BASE_URL}logo.png`}
        alt="Public Blog Logo"
        onClick={() => navigate("posts")}
      />
      <NavigationButton onClick={() => navigate("posts")}>Posts</NavigationButton>
      <NavigationButton onClick={() => navigate("new_post")}>New Post</NavigationButton>
      <NavigationButton onClick={() => navigate("post")}>Post</NavigationButton>
      <NavigationButton onClick={() => navigate("login")}>Login</NavigationButton>
    </div>
  )
}

export default Header;
