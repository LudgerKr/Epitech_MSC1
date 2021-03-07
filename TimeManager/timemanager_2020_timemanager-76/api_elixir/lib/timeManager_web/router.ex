defmodule TimeManagerWeb.Router do
  use TimeManagerWeb, :router

  alias TimeManager.Guardian

  pipeline :api do
    plug CORSPlug, [origin: "*"]
    plug :accepts, ["json"]
  end

  pipeline :jwt_authenticated do
    plug Guardian.AuthPipeline
  end

  pipeline :api_admin do
    plug(Guardian.Permissions.Bitwise, ensure: %{administrator: [:all]})
  end

  pipeline :api_manager do
    plug(Guardian.Permissions.Bitwise, ensure: %{manager: [:all]})
  end

  # Non connecté
  scope "/api", TimeManagerWeb do
    pipe_through :api
    post "/sign_in", UserController, :sign_in
    post "/sign_up", UserController, :create
  end

  # Droit de tout le monde
  scope "/api/v1", TimeManagerWeb do
    pipe_through [:api, :jwt_authenticated]
    get "/profile/:id", UserController, :show
    put "/profile/:id", UserController, :update
    delete "/profile/:id", UserController, :delete
    post "/sign_out", UserController, :sign_out
    get "/workingtimes/:user_id", WorkingtimeController, :show_workingtimesWithUserId

    post "/clocks/:user_id", ClockController, :create_clockWithoutUserId
    resources "/clocks", ClockController, except: [:new, :edit]

  end

  # Droit manager
  scope "/api/manager", TimeManagerWeb do
    pipe_through [:api, :jwt_authenticated]
    resources "/users", UserController, except: [:new, :edit]
    resources "/clocks", ClockController, except: [:new, :edit]
    get "/clocks/users/:user_id", ClockController, :show_usersID
    resources "/teams", TeamController, except: [:new, :edit]

    get "/users/teams/:team_id", UserController, :show_users_per_team
    resources "/workingtimes", WorkingtimeController, except: [:new, :edit]
  end

  # Droit admin
  scope "/api/admin", TimeManagerWeb do
    pipe_through [:api, :jwt_authenticated]
    resources "/users", UserController, except: [:new, :edit]
    resources "/clocks", ClockController, except: [:new, :edit]
    get "/clocks/users/:user_id", ClockController, :show_usersID

    resources "/workingtimes", WorkingtimeController, except: [:new, :edit]
    get "/users/teams/:team_id", UserController, :show_users_per_team
    resources "/roles", RoleController, except: [:new, :edit]
    resources "/teams", TeamController, except: [:new, :edit]
  end
  # Enables LiveDashboard only for development
  #
  # If you want to use the LiveDashboard in production, you should put
  # it behind authentication and allow only admins to access it.
  # If your application does not have an admins-only section yet,
  # you can use Plug.BasicAuth to set up some basic authentication
  # as long as you are also using SSL (which you should anyway).
  if Mix.env() in [:dev, :test] do
    import Phoenix.LiveDashboard.Router

    scope "/" do
      pipe_through [:fetch_session, :protect_from_forgery]
      live_dashboard "/dashboard", metrics: TimeManagerWeb.Telemetry
    end
  end
end
