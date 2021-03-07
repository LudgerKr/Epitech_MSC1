FROM node:12.16
WORKDIR /app

RUN npm install -g http-server 
RUN npm install --save

COPY package*.json ./
RUN npm install

COPY . ./

EXPOSE 8080
CMD [ "npm", "run", "serve"]