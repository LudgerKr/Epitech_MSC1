FROM bitwalker/alpine-elixir-phoenix:latest

WORKDIR app

ENV MIX_ENV=dev

COPY mix.exs .
COPY mix.lock .
RUN mkdir assets

RUN mix deps.clean --all && mix deps.get && mix compile

COPY . .

CMD mix phx.server
