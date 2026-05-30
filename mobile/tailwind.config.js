/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./app/**/*.{js,jsx,ts,tsx}",
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  presets: [require("nativewind/preset")],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: "#1a237e",
          light: "#534bae",
          dark: "#000051",
        },
        accent: {
          DEFAULT: "#ff6f00",
          light: "#ffa000",
          dark: "#c43e00",
        },
        surface: "#f5f5f5",
        card: "#ffffff",
      },
    },
  },
  plugins: [],
};
