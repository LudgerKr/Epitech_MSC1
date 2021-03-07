# This file is responsible for configuring your application
# and its dependencies with the aid of the Mix.Config module.
#
# This configuration file is loaded before any dependency and
# is restricted to this project.

# General application configuration
use Mix.Config

config :timeManager,
  namespace: TimeManager,
  ecto_repos: [TimeManager.Repo]

# Configures the endpoint
config :timeManager, TimeManagerWeb.Endpoint,
  url: [host: "localhost"],
  secret_key_base: "22mNZtn0ph0CKUuNGqKa99ODqBC4F5GgniFvPgTRbYRVcYDKk2hwOE3E+ZY5FuLD",
  render_errors: [view: TimeManagerWeb.ErrorView, accepts: ~w(json), layout: false],
  pubsub_server: TimeManager.PubSub,
  live_view: [signing_salt: "ocGm1KbS"]

# Configures Elixir's Logger
config :logger, :console,
  format: "$time $metadata[$level] $message\n",
  metadata: [:request_id]

# Use Jason for JSON parsing in Phoenix
config :phoenix, :json_library, Jason

config :timeManager, TimeManager.Guardian,
       issuer: "timeManager",
       secret_key: "pJMHiUP2F2rLopl8sioQ0pp4W8HKBaqzACDYbWXMplL9xTSF2YLIf54ErNrjQCDX",
       permissions: %{
        manager: [:all],
        administrator: [:all]
       }
# Import environment specific config. This must remain at the bottom
# of this file so it overrides the configuration defined above.
import_config "#{Mix.env()}.exs"
