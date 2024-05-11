import PostCard from "@/components/PostCard.tsx";
import { useContext, useEffect, useState } from "react";
import { ApplicationContext } from "@/lib/context.ts";
import { Button } from "@/components/common/Button.tsx";
import { Page, PostData } from "@/lib/types.ts";
import superagent from "superagent";
import { Config } from "@/lib/config.ts";

const PostsPage = () => {
  const appContext = useContext(ApplicationContext);
  const [posts, setPosts] = useState<PostData[]>();

  useEffect(() => {
    superagent.get(`${Config.API_URL}posts`)
              .then(response => setPosts(response.body));
  }, [appContext.data]);
  
  if (!posts)
    return <>Loading...</>
  
  const navigate = (page : Page) => {
    appContext.setData(prevData => ({...prevData, page: page}))
  }
  
  return(
    <div className="flex flex-col w-full my-10 items-center gap-10">
      <span className="text-heading">Posts</span>
      <div className="flex flex-col w-full items-center gap-4">
        {posts.length ?
          posts.map((post) =>
            <PostCard post={post} key={post.id}/>
          ) :
          <span className="block w-full text-center">
            No posts
          </span>
        }
      </div>
      <Button onClick={() => navigate("new_post")}>
        New Post
      </Button>
    </div>
  )
}

export default PostsPage;
