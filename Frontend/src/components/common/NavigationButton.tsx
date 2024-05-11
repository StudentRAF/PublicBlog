import * as React from "react"
import { cn } from "@/lib/utils.ts";


const NavigationButton = React.forwardRef<HTMLButtonElement, React.ButtonHTMLAttributes<HTMLButtonElement>>(
  ({className, ...props}, ref) => {
    return(
      <button
        className={cn(
          "h-full px-4 border-b border-transparent hover:border-blue-400",
          className
        )}
        ref={ref}
        {...props}/>
    )
  }
)

export default NavigationButton;
