import { HTMLAttributes, useState } from "react";
import ContentEditable from "react-contenteditable";
import colorSchemeJson from "../data/color-schemes.json";
import ColorInspector from "color";

interface ColorCircleProps {
  color: string;
  name: string;
  id: number;
}

interface Color {
  color: string;
  name: string;
}

interface ColorScheme {
  colors: Color[];
}

const ColorSchemes = () => {
  const [colorSchemes, setColorSchemes] = useState(colorSchemeJson);

  return (
    <div className="grid grid-cols-3 gap-4 w-5/6 p-8 border-2 border-[goldenrod] border-opacity-50 rounded bg-neutral-900">
      {colorSchemes.map((colorScheme: Color[], index: number) => (
        <ColorScheme key={index} colors={colorScheme} />
      ))}
    </div>
  );
};

const ColorScheme: React.FC<ColorScheme> = ({ colors }) => {
  const [colorCodes, setColorCodes] = useState(colors);

  return (
    <div className="flex flex-col items-center justify-center gap-2">
      {colorCodes.map((color, index) => (
        <ColorCircle key={index} color={color.color} name={color.name} />
      ))}
    </div>
  );
};

const ColorCircle: React.FC<ColorCircleProps> = ({ color, name }) => {
  const [colorCode, setColorCode] = useState(color);

  const textColorCode = ColorInspector(colorCode).isDark() ? "white" : "black";

  const style: HTMLAttributes<HTMLDivElement>["style"] = {
    backgroundColor: colorCode,
    color: textColorCode,
  };

  return (
    <div
      key={name}
      className="w-24 h-24 rounded-full border-2 border-neutral-500 border-opacity-50 flex items-center justify-center"
      style={style}
      onBlur={(e) => setColorCode(e.currentTarget.innerText)}
    >
      <ContentEditable
        tagName="span"
        className="text-sm font-medium"
        html={color}
        onChange={() => {
          return;
        }}
      />
    </div>
  );
};
export default ColorSchemes;
