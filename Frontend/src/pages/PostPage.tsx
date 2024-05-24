import { Input } from "@/components/common/Input.tsx";
import { Textarea } from "@/components/common/Textarea.tsx";
import { Button } from "@/components/common/Button.tsx";
import { ChangeEvent, FormEvent, useContext, useEffect, useState } from "react";
import { CreateCommentFormData, HandleChangeEventData, ParameterFormData, PostData } from "@/lib/types.ts";
import { ApplicationContext, ApplicationContextData } from "@/lib/context.ts";
import superagent from "superagent";
import { Config } from "@/lib/config.ts";

function initFormData(appContext : ApplicationContextData) : CreateCommentFormData {
  const postId = Number(appContext.data.path);

  if (!appContext.data.user || !appContext.data.path || isNaN(postId))
    throw new Error("Invalid Application State | Cannot Initialise Form Data")

  return {
    author_id: appContext.data.user.id,
    post_id: postId,
    content: "",
  }
}

const PostPage = () => {
  const appContext = useContext(ApplicationContext);
  const [post, setPost] = useState<PostData>();
  const [formData, setFormData] = useState<CreateCommentFormData>(initFormData(appContext));

  if (!appContext.data.path)
    throw new Error("Invalid Application State | No Path")

  useEffect(() => {
    superagent.get(`${Config.API_URL}posts/${appContext.data.path}`)
              .set("Authorization", `Bearer ${appContext.data.authorization?.token}`)
              .then(response => setPost(response.body));
  }, [appContext.data]);
  
  if (!post)
    return <>Loading...</>;
  
  const handleChange = (event : ChangeEvent<HandleChangeEventData>) => {
    const parameter : ParameterFormData = event.target;

    if (!parameter.name)
      return;

    setFormData(previous => ({ ...previous, [parameter.name]: parameter.value}) )
  }

  const submitComment = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    superagent.put(`${Config.API_URL}posts`)
              .set("Authorization", `Bearer ${appContext.data.authorization?.token}`)
              .send(formData)
              .then(() => {
                setFormData(prevData => ({...prevData, content: ""}));
                appContext.setData(prevData => ({...prevData, page: "post" }))
              });
    event.currentTarget.reset();
  }

  const date = post.date instanceof Date ? post.date.toDateString() : post.date;

  return (
    <div className="flex flex-col mt-10 mb-24 w-full items-center">
      <div className="flex flex-col w-200 items-center mx-auto gap-10">

        <span className="px-4 text-heading">{post.title}</span>
        <p className="self-start px-4 border-gray-900 whitespace-pre-wrap">{post.content}</p>

        <div className="flex w-full py-6 px-4 justify-between border-y border-gray-900">
          <span>Date: {date}</span>
          <span>Author: {post.author.first_name} {post.author.last_name}</span>
        </div>

        <div className="flex flex-col w-160 gap-5 items-center">
          <span className="text-title">
            Comments
          </span>

          <div className="w-full">
            {post.comments.length > 0 ?
              post.comments.map((comment) => (
              <div className="flex flex-col px-4 py-6 border-gray-900 [&:not(:last-child)]:border-b">
                <span>{comment.author.first_name} {comment.author.last_name}</span>
                <p className="whitespace-pre-wrap">{comment.content}</p>
              </div>
              )) :
              <span className="block w-full text-center">
                No comments
              </span>
            }
          </div>
        </div>
        <form className="flex flex-col w-160 gap-5 items-center" onSubmit={submitComment}>
          <span className="text-title">
            New Comment
          </span>
          <div className="flex flex-col w-full gap-2">
            <label className="ml-2">
              Username
            </label>
            <Input placeholder="username" value={`${appContext.data.user?.first_name} ${appContext.data.user?.last_name}`} disabled />
          </div>

          <div className="flex flex-col w-full gap-2">
            <label className="ml-2">
              Comment
            </label>
            <Textarea className="h-36" placeholder="comment" name="content" onChange={handleChange}/>
          </div>
          <Button className="w-fit" type="submit">
            Save Comment
          </Button>
        </form>

      </div>
    </div>
  )
}

export default PostPage;
