# EAD E-Commerce

Microservices e-commerce platform (EAD assignment) in a single mono repo.

## Configuration

All settings come from `.env` (see `.env.example`):

| Variable | Purpose |
| --- | --- |
| `MYSQL_ROOT_PASSWORD` | Root password for the three MySQL containers (customer / product / order) |
| `MONGO_USER`, `MONGO_PASSWORD` | MongoDB credentials for the payment service |
| `MYSQL_CUSTOMER_HOST_PORT` | Host port for the customer MySQL database (default: 3307) |
| `MYSQL_PRODUCT_HOST_PORT` | Host port for the product MySQL database (default: 3308) |
| `MYSQL_ORDER_HOST_PORT` | Host port for the order MySQL database (default: 3309) |
| `MONGO_HOST_PORT` | Host port for MongoDB (default: 27018) |
| `JWT_SECRET` | Base64 HMAC key shared by gateway and customer service |
| `STRIPE_SECRET_KEY` | Stripe secret key for the payment service |
| `MAIL_*` | SMTP settings for the customer service (`MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD`) |
| `FRONTEND_PORT` | Host port for the frontend application (default: 3000) |
| `FRONTEND_ORIGIN` | Origin allowed by gateway CORS (default: http://localhost:3000) |
