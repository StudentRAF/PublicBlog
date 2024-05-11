import { PostData } from "@/lib/types.ts";
import { useContext } from "react";
import { ApplicationContext } from "@/lib/context.ts";

export type PostCardProp = {
  post: PostData
}

const PostCard = ({ post } : PostCardProp) => {
  const date: string = post.date instanceof Date ? post.date.toLocaleDateString() : post.date;
  const appContext = useContext(ApplicationContext);

  return(
    <div
      className="flex flex-col w-160 gap-4 border border-gray p-8 rounded-large hover:bg-metal-900/40 hover:cursor-pointer"
      onClick={() => appContext.setData(prevData => ({
        ...prevData,
        path: post.id,
        page: "post",
      }))}
      // {
      //   appContext.path = post.id;
      //   navigation.navigate("post");
      // }}
    >
      <div className="flex flex-col gap-2">
        <span className="text-title text-gray-50">{post.title}</span>
        <span className="text-normal text-gray-300 line-clamp-3">{post.content}</span>
      </div>
      <div className="flex justify-between">
        <span className="text-small text-gray-500">{date}</span>
        <span className="text-small text-gray-500">{post.author.first_name} {post.author.last_name}</span>
      </div>
    </div>
  )
}

export default PostCard;
