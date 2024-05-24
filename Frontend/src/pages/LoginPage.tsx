import { ChangeEvent, FormEvent, useContext, useState } from "react";
import { ApplicationContext } from "@/lib/context.ts";
import { Input } from "@/components/common/Input.tsx";
import { Button } from "@/components/common/Button.tsx";
import { HandleChangeEventData, LoginFormData, ParameterFormData, UserData, UserToken } from "@/lib/types.ts";
import superagent from "superagent";
import { Config } from "@/lib/config.ts";

function initFormData() : LoginFormData {
  return {
    username: "",
    password: "",
  }
}

const LoginPage = () => {
  const appContext = useContext(ApplicationContext);
  const [formData, setFormData] = useState<LoginFormData>(initFormData());

  const handleChange = (event : ChangeEvent<HandleChangeEventData>) => {
    const parameter : ParameterFormData = event.target;

    if (!parameter.name)
      return;

    setFormData(previous => ({ ...previous, [parameter.name]: parameter.value}));
  }

  const submitForm = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    superagent.post(`${Config.API_URL}users`)
      .send(formData)
      .then(response => {
        const user : UserData = response.body;
        const authorization : UserToken = {
          token: response.headers["authorization"],
        }

        appContext.setData(prevData => ({...prevData, authorization: authorization, user: user, page: "posts"}));
      });
  }

  return (
    <div className="flex flex-col w-full my-10 items-center gap-10">
      <span className="text-heading">
        Login
      </span>
      <form className="flex flex-col gap-10 w-96 items-center" onSubmit={submitForm}>
        <div className="flex flex-col w-full gap-5">
          <div className="flex flex-col gap-2">
            <label className="ml-2">
              Username
            </label>
            <Input placeholder="username" name="username" onChange={handleChange}/>
          </div>

          <div className="flex flex-col gap-2">
            <label className="ml-2" id="title">
              Password
            </label>
            <Input placeholder="password" name="password" onChange={handleChange}/>
          </div>
        </div>
        <Button className="w-fit" type="submit">
          Login
        </Button>
      </form>
    </div>
  )
}

export default LoginPage;