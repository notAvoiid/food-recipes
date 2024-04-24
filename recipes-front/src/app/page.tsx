import Link from "next/link";

const mockData = [
  {
    id: 1,
    title: "Receita Pão de Queijo",
    name: "Pão de Queijo",
    ingredients: "Farinha, Trigo e Queijo",
    preparation: "Preparation for Pão de Queijo",
  },
  {
    id: 2,
    title: "Receita Bolo de Chocolate",
    name: "Bolo de Chocolate",
    ingredients: "Farinha, Trigo,Ovo, Manteiga, Chocolate",
    preparation: "Preparation for Bolo de Chocolate",
  },
  {
    id: 3,
    title: "Receita Omelete",
    name: "Omelete",
    ingredients: "Ovo, Presunto, Queijo, Tomate, Cebola",
    preparation: "Preparation Omelete",
  },
];

// const mockDataItem = mockData.map((item) => ({
//   ...item,
// }));

export default function HomePage() {
  return (
    <main className="">
      <div className="flex flex-wrap justify-center gap-4">
        {[...mockData, ...mockData, ...mockData].map((item) => (
          <div key={item.id} className="w-48 border">
            <h2>{item.title}</h2>
            <p>{item.name}</p>
            <p>{item.ingredients}</p>
            <p>{item.preparation}</p>
            {/* <Link href={`/recipe/${item.id}`}>
              <a>Ver receita</a>
            </Link> */}
          </div>
        ))}
      </div>
    </main>
  );
}
