import * as React from "react"

import { cn } from "@/lib/utils.ts"

export interface TextareaProps
  extends React.TextareaHTMLAttributes<HTMLTextAreaElement> {}

const Textarea = React.forwardRef<HTMLTextAreaElement, TextareaProps>(
  ({ className, ...props }, ref) => {
    return (
      <textarea
        className={cn(
          "flex min-h-20 w-full px-6 py-5 rounded-large bg-metal-950 border border-gray-500 text-normal resize-none outline-none placeholder:text-gray-500 hover:border-blue-400 focus:border-blue-400 focus:bg-gray-900/40 disabled:cursor-not-allowed disabled:opacity-50",
          className
        )}
        ref={ref}
        {...props}
      />
    )
  }
)
Textarea.displayName = "Textarea"

export { Textarea }
