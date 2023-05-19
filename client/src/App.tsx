import ColorSchemes from "./components/ColorSchemes";

function App() {
  return (
    <div className="min-h-screen bg-neutral-800 flex flex-col items-center justify-center">
      <h1 className="text-3xl text-[goldenrod]">Hello World!</h1>
      <ColorSchemes />
    </div>
  );
}

export default App;
