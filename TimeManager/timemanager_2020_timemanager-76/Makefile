##
## EPITECH PROJECT, 2020
## Makefile
## File description:
## toto
##

db:
	cd api_elixir && mix ecto.drop
	cd api_elixir && mix ecto.create && mix ecto.migrate
	bash insertdb.sh

install:
	cd client_vuejs && npm install

dev:
	make -j 2 run_elixir run_client

run_elixir:
	     cd api_elixir && mix phx.server

run_client:
	     cd client_vuejs && npm run serve