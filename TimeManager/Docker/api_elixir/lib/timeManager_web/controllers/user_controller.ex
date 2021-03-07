defmodule TimeManagerWeb.UserController do
  use TimeManagerWeb, :controller

  alias TimeManager.Users
  alias TimeManager.Users.User
  alias TimeManager.Guardian

  action_fallback TimeManagerWeb.FallbackController

  def index(conn, _params) do
    users = Users.list_users()
    render(conn, "index.json", users: users)
  end

  def create(conn, %{"user" => user_params}) do
    with {:ok, %User{} = user} <- Users.create_user(user_params),
         {:ok, token, _claims} <- Guardian.encode_and_sign(user, %{role: user.role_id}) do
      conn |> render("jwt.json", jwt: token)
      end
    end

  def show(conn, %{"id" => id}) do
    user = Users.get_user!(id)
    render(conn, "show.json", user: user)
  end

  def show_users_per_team(conn, %{"team_id" => team_id}) do
    users = Users.get_user_per_team!(team_id)
    render(conn, "index.json", users: users)
  end

  def update(conn, %{"id" => id, "user" => user_params}) do
    user = Users.get_user!(id)

    with {:ok, %User{} = user} <- Users.update_user(user, user_params) do
      render(conn, "show.json", user: user)
    end
  end

  def delete(conn, %{"id" => id}) do
    user = Users.get_user!(id)

    with {:ok, %User{}} <- Users.delete_user(user) do
      send_resp(conn, :no_content, "")
    end
  end

  def sign_in(conn, %{"email" => email, "password" => password, "role_id" => role_id}) do
    case Users.token_sign_in(email, password, role_id) do
      {:ok, token, _claims} ->
        conn
        |> render("jwt.json", jwt: token)
      _ ->
        {:error, :unauthorized}
    end
  end

  def sign_out(conn, _) do
    conn
    |> Guardian.Plug.sign_out()
    |> render("sign_out.json", message: "User disconnected")
  end

end