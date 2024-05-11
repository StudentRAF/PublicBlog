import * as React from "react"

import { cn } from "@/lib/utils.ts"

export interface InputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {}

const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className, type, ...props }, ref) => {
    return (
      <input
        type={type}
        className={cn(
          "flex h-10 w-full px-6 rounded-full bg-metal-950 border border-gray-500 text-normal outline-none placeholder:text-gray-500 hover:border-blue-400 focus:border-blue-400 focus:bg-gray-900/40 disabled:text-gray-400",
          className
        )}
        ref={ref}
        {...props}
      />
    )
  }
)
Input.displayName = "Input"

export { Input }
