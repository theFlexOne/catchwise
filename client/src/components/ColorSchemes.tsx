import colorSchemeJson from "../data/color-schemes.json";

interface Color {
  color: string;
  name: string;
}

const colorSchemes = colorSchemeJson;

const ColorSchemes = () => {
  console.log(colorSchemeJson);

  return (
    <div className="grid grid-cols-3 gap-4 w-5/6 p-8 border-2 border-[goldenrod] border-opacity-50 rounded bg-neutral-900">
      {colorSchemes.map((colorScheme: Color[], index: number) => (
        <div key={index} className="flex flex-col items-center justify-center">
          {colorScheme.map((color: Color, index: number) => (
            <div
              key={index}
              className="w-16 h-16 rounded-full border-2 border-[goldenrod] border-opacity-50"
              style={{ backgroundColor: color.color }}
            ></div>
          ))}
        </div>
      ))}
    </div>
  );
};

export default ColorSchemes;
