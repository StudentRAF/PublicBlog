import { Button } from "@/components/common/Button.tsx";
import { ChangeEvent, FormEvent, useContext, useState } from "react";
import { ApplicationContext, ApplicationContextData } from "@/lib/context.ts";
import { Input } from "@/components/common/Input.tsx";
import { Textarea } from "@/components/common/Textarea.tsx";
import { CreatePostFormData, HandleChangeEventData, ParameterFormData } from "@/lib/types.ts";
import superagent from "superagent"
import { Config } from "@/lib/config.ts";

function initFormData(appContext : ApplicationContextData) : CreatePostFormData {
  if (!appContext.data.user)
    throw new Error("Invalid Application State | Cannot Initialise Form Data")

  return {
    author_id: appContext.data.user.id,
    title: "",
    content: "",
  }
}

const NewPostPage = () => {
  const appContext = useContext(ApplicationContext);
  const [formData, setFormData] = useState<CreatePostFormData>(initFormData(appContext));

  const handleChange = (event : ChangeEvent<HandleChangeEventData>) => {
    const parameter : ParameterFormData = event.target;

    if (!parameter.name)
      return;

    setFormData(previous => ({ ...previous, [parameter.name]: parameter.value}) )
  }

  const submitForm = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    superagent.post(`${Config.API_URL}posts`)
              .set("Authorization", `Bearer ${appContext.data.authorization?.token}`)
              .send(formData)
              .then(() => { appContext.setData(prevData => ({...prevData, page: "posts" }))});
  }

  return (
    <div className="flex flex-col w-full my-10 items-center gap-10">
      <span className="text-heading">
        New Post
      </span>
      <form className="flex flex-col gap-10 w-160 items-center" onSubmit={submitForm}>
        <div className="flex flex-col w-full gap-5">
          <div className="flex flex-col gap-2">
            <label className="ml-2">
              Author
            </label>
            <Input placeholder="author" value={`${appContext.data.user?.first_name} ${appContext.data.user?.last_name}`} disabled />
          </div>

          <div className="flex flex-col gap-2">
            <label className="ml-2" id="title">
              Title
            </label>
            <Input placeholder="title" name="title" onChange={handleChange} />
          </div>

          <div className="flex flex-col gap-2">
            <label className="ml-2">
              Content
            </label>
            <Textarea className="h-48" placeholder="content" name="content" onChange={handleChange} />
          </div>
        </div>
        <Button className="w-fit" type="submit">
          Save Post
        </Button>
      </form>
    </div>
  )
}

export default NewPostPage;
