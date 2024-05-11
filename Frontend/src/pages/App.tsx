import { useEffect, useState } from "react";
import PostPage from "@/pages/PostPage.tsx";
import PostsPage from "@/pages/PostsPage.tsx";
import NewPostPage from "@/pages/NewPostPage.tsx";
import Header from "@/components/Header.tsx";
import { ApplicationContext, ApplicationData, defaultAppData } from "@/lib/context.ts";
import LoginPage from "@/pages/LoginPage.tsx";

const App = () => {
  const [appData, setAppData] = useState<ApplicationData>(defaultAppData);

  useEffect(() => {
    setAppData(prevData => ({...prevData, user: { id: 1, first_name: "Name", last_name:"Last Name", username: "Username"}}))
  }, [])

  return (
    <div className="flex flex-col min-h-screen bg-metal-950">
      <ApplicationContext.Provider value={{
        setData: setAppData,
        data: appData,
      }}>
        <Header />
        <div className="flex flex-1">
          {(() => {
            if (appData.user)
              switch(appData.page) {
                case "login":    return <LoginPage />;
                case "new_post": return <NewPostPage />;
                case "post":     return <PostPage />;
                case "posts":    return <PostsPage />;
              }

            return <LoginPage />
          })()}
        </div>
      </ApplicationContext.Provider>
    </div>
  )
}

export default App
