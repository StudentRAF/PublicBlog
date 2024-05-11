import * as React from "react"
import { Slot } from "@radix-ui/react-slot"
import { cva, type VariantProps } from "class-variance-authority"

import { cn } from "@/lib/utils"

const buttonVariants = cva(
  "inline-flex items-center justify-center whitespace-nowrap rounded-full font-medium transition-colors disabled:pointer-events-none",
  {
    variants: {
      variant: {
        default: "bg-blue-500 hover:bg-blue-600 disabled:bg-gray-400 disabled:text-gray-100",
        outline: "bg-transparent text-gray-100 border border-gray-400 hover:bg-gray-900 disabled:bg-gray-400 disabled:text-gray-100",
      },
      size: {
        default: "h-10 px-6 text-normal",
        small:   "h-9  px-4 text-small",
        large:   "h-11 px-8 text-large",
        icon:    "h-10 w-10 rounded-normal",
      },
    },
    defaultVariants: {
      variant: "default",
      size: "default",
    },
  }
)

export interface ButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement>,
    VariantProps<typeof buttonVariants> {
  asChild?: boolean
}

const Button = React.forwardRef<HTMLButtonElement, ButtonProps>(
  ({ className, variant, size, asChild = false, ...props }, ref) => {
    const Component = asChild ? Slot : "button"
    return (
      <Component
        className={cn(buttonVariants({ variant, size, className }))}
        ref={ref}
        {...props}
      />
    )
  }
)
Button.displayName = "Button"

export { Button, buttonVariants }
