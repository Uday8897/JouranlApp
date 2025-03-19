import React, { useState } from "react";

const FEWD = () => {
  const products = [
    { id: 1, name: "Ice Cream", price: 5.0, image: "ice-cream.jpg" },
    { id: 2, name: "Water Melon", price: 10.0, image: "watermelon.jpg" },
    { id: 3, name: "Donut", price: 100.0, image: "donut.jpg" },
  ];

  const [cart, setCart] = useState(0);
  const [price, setPrice] = useState(0);

  const cartAddHandler = (id) => {
    setCart(cart + 1);
    const currentProd = products.find((prod) => prod.id === id);
    setPrice(price + currentProd.price);
  };

  const cartRemoveHandler = (id) => {
    const currentProd = products.find((prod) => prod.id === id);
    if (!currentProd || cart === 0) {
      return;
    }
    setCart(cart - 1);
    setPrice(price - currentProd.price);
  };
  

  return (
    <div>
      <div className="bg-slate-400 h-20 w-full p-5">
        <h1>Shopping Cart: {cart} total Items</h1>
        <h1>Total: {price}</h1>
      </div>
      {products.map((product) => (
        <div key={product.id} className="mx-10 space-x-10">
          <img
            src={product.image}
            alt={product.name}
            className="mx-10 w-24 h-24 rounded-full"
          />
          <h1 className="text-2xl">{product.name}</h1>
          <h1>{product.price}</h1>
          <button
            className="bg-blue-400 h-10 w-20 rounded-xl"
            onClick={() => cartAddHandler(product.id)}
          >
            Add
          </button>
          <button
            className="bg-blue-400 h-10 w-20 rounded-xl"
            onClick={() => cartRemoveHandler(product.id)}
          >
            Remove
          </button>
        </div>
      ))}
    </div>
  );
};

export default FEWD;
