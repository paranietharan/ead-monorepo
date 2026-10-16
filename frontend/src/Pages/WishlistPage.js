import { Box } from '@mui/material';
import React from 'react'
import NavBar from '../Components/Utils/NavBar';
import WishListCard from "../Components/WishLishCard/WishListCard"
const wishlistPage = () => {

         const products = [
    {
      id: 1,
      image: 'https://via.placeholder.com/100',
      title: 'T-shirt with multiple colors',
      details: 'Size: medium, Color: blue, Material: Plastic\nSeller: Artel Market',
      price: 39.0,
    },
    {
      id: 2,
      image: 'https://via.placeholder.com/100',
      title: 'Winter Jacket',
      details: 'Size: large, Color: brown, Material: Wool\nSeller: Winter Wear Co.',
      price: 79.0,
    },
    {
      id: 3,
      image: 'https://via.placeholder.com/100',
      title: 'Denim Shorts',
      details: 'Size: small, Color: black, Material: Denim\nSeller: Artel Market',
      price: 29.0,
    },
  ];

  const handleDelete = (id) => {
    console.log(`Delete item with id: ${id}`);
  };

  const handleAddToCart = (id) => {
    console.log(`Add to cart item with id: ${id}`);
  };
  return (
    <div>
      <Box
        sx={{
          height: "calc(100vh - 40px)",
          p: "20px",
        }}
      >
        <Box
          sx={{
            backgroundColor: "bgSoft.main",
            height: "100%",
            borderRadius: "32px",
            // m: "20px",
          }}
        >
          <NavBar />
          <div>
      {products.map((product) => (
        <WishListCard
          key={product.id}
          image={product.image}
          title={product.title}
          details={product.details}
          price={product.price}
          onDelete={() => handleDelete(product.id)}
          onAddToCart={() => handleAddToCart(product.id)}
        />
      ))}
    </div>
        </Box>
      </Box>
    </div>
  )
}

export default wishlistPage