import React from 'react';
import { Card, CardMedia, Typography, Button, IconButton, Box } from '@mui/material';
import DeleteOutlinedIcon from '@mui/icons-material/DeleteOutlined';

const WishlistItem = ({ image, title, details, price, onDelete, onAddToCart }) => {
  return (
    <Card
      sx={{
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: 2,
        borderRadius: 3,
        boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)',
        maxWidth: 600,
        marginBottom: 2, // Add spacing between items if used in a list
      }}
    >
      {/* Image */}
      <CardMedia
        component="img"
        image={image}
        alt={title}
        sx={{
          width: 100,
          height: 100,
          borderRadius: 2,
        }}
      />

      {/* Content */}
      <Box sx={{ flex: 2, marginLeft: 2, textAlign: 'left' }}>
        <Typography variant="h6" component="div">
          {title}
        </Typography>
        <Typography
          variant="body2"
          color="text.secondary"
          sx={{ marginTop: 1, marginBottom: 1 }}
        >
          {details}
        </Typography>
        <Typography variant="h6" component="div" color="text.primary">
          ${price}
        </Typography>
      </Box>

      {/* Actions */}
      <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <IconButton color="default" onClick={onDelete}>
          <DeleteOutlinedIcon />
        </IconButton>
        <Button
          variant="outlined"
          size="small"
          sx={{
            marginTop: 1,
            textTransform: 'none',
            borderRadius: 3,
          }}
          onClick={onAddToCart}
        >
          Add to Cart
        </Button>
      </Box>
    </Card>
  );
};

export default WishlistItem;
