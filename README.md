# EAD E-Commerce — Group Project

An **Enterprise Application Development module group assignment** exploring an e-commerce system with Spring Boot services, a Node.js/Express payment service, and a React frontend in one repository.

## Individual contribution

[Paranietharan](https://github.com/paranietharan) was responsible for the **product service**. The other services and the overall application are part of the group's shared work.

The [product service](services/product) contains product and category management, cart and wishlist operations, persistence, and request/response mapping. Its source is the main entry point for reviewing this individual contribution:

| Area | Source |
| --- | --- |
| Products | [Product package](services/product/src/main/java/com/teamz/product/product) |
| Categories | [Category package](services/product/src/main/java/com/teamz/product/category) |
| Shopping cart | [Cart package](services/product/src/main/java/com/teamz/product/cart) |
| Wishlist | [Wishlist package](services/product/src/main/java/com/teamz/product/Wishlist) |
| Dependencies and build | [Product pom.xml](services/product/pom.xml) |

## Architecture

| Component | Purpose |
| --- | --- |
| [Frontend](frontend) | React interface; current routes include home and wishlist |
| [Gateway](services/gateway) | API routing and authentication filter |
| [Customer](services/customer) | Customer accounts and authentication |
| [Product](services/product) | Products, categories, carts, and wishlists |
| [Order](services/order) | Order operations |
| [Payment](services/payment) | Node.js/Express integration with Stripe and MongoDB |
| [Config server](services/config-server) | Central service configuration |
| [Discovery](services/discovery) | Service discovery |

Docker Compose provisions separate MySQL databases for customer, product, and order services, plus MongoDB for payments. The Spring services declare Java 17; Dockerfiles provide the application build environments.

## Run locally with Docker Compose

```bash
git clone https://github.com/paranietharan/ead-monorepo.git
cd ead-monorepo
cp .env.example .env
```

Set your local database passwords, a generated base64 JWT secret, and a Stripe test secret key in `.env` before starting. For example, `openssl rand -base64 48` generates a new secret value. Configure SMTP when exercising password-reset/email flows. Do not commit the populated `.env`.

```bash
docker compose config --quiet
docker compose up --build -d
docker compose ps
```

Default local addresses:

- Frontend: `http://localhost:3000`
- API gateway: `http://localhost:8222`
- Service discovery: `http://localhost:8761`

Inspect a service with `docker compose logs -f product`. Stop the stack with `docker compose down`; database volumes are retained unless explicitly removed.

## Configuration

Compose settings are read from `.env` (see [.env.example](.env.example)):

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

## Implementation scope

This is a group coursework implementation. Service separation demonstrates architecture and integration work, but it should not be read as a claim of a complete production checkout flow. The current React router exposes home and wishlist pages; the backend contains broader functionality.

The payment controller currently contains a localhost return URL. Check source-level integration settings as well as `.env` when changing deployment addresses. Use Stripe test credentials while evaluating the assignment.

## Reviewing the product contribution

Start with the product controllers, then trace requests through their services, repositories, and DTO mappings. A useful demonstration covers category creation, product operations, and cart/wishlist behavior against the product database.

The product module includes a Spring application-context test. Dedicated product, cart, wishlist, and cross-service workflow tests remain useful follow-up work.
