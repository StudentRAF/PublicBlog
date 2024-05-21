export type HandleChangeEventData = HTMLInputElement | HTMLTextAreaElement;

export type Page = "post" | "posts" | "new_post" | "login";

export type PostData = {
  id:       number,
  title:    string,
  content:  string,
  date:     string | Date,
  author:   UserData,
  comments: CommentData[]
}

export type CommentData = {
  id:      number,
  author:  UserData,
  content: string,
  date:    string | Date
}

export type   UserData = {
  id:         number,
  first_name: string,
  last_name:  string,
  username:   string,
}

export type CreatePostFormData = {
  author_id: number,
  title:     string,
  content:   string,
}

export type ParameterFormData = {
  name: string,
  value: string,
}

export type CreateCommentFormData = {
  author_id: number,
  post_id:   number,
  content:   string,
}

export type LoginFormData = {
  username: string,
  password: string
}
