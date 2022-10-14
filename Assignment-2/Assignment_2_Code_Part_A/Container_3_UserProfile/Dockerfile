FROM node:13.12.0-alpine
WORKDIR /container_3
ENV PATH /container_3/node_modules/.bin:$PATH
COPY package.json ./
COPY package-lock.json ./
RUN npm install
COPY . ./
CMD ["npm", "start"]