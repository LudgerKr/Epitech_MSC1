defmodule TimeManager.Guardian.AuthPipeline do
  use Guardian.Plug.Pipeline, otp_app: :timeManager,
  module: TimeManager.Guardian,
  error_handler: TimeManager.AuthErrorHandler

  plug Guardian.Plug.VerifyHeader, realm: "Bearer"
  plug Guardian.Plug.EnsureAuthenticated
  plug Guardian.Plug.LoadResource
end