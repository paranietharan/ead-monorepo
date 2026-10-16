FROM node:20-alpine
ENV NODE_ENV=production
WORKDIR /app

COPY package*.json ./
RUN npm ci --omit=dev

COPY . .
USER node

EXPOSE 8010
# `npm start` uses nodemon (dev); in the container run node directly
CMD ["node", "server.js"]
