defmodule TimeManagerWeb.UserView do
  use TimeManagerWeb, :view
  alias TimeManagerWeb.UserView

  def render("index.json", %{users: users}) do
    %{data: render_many(users, UserView, "user.json")}
  end

  def render("show.json", %{user: user}) do
    %{data: render_one(user, UserView, "user.json")}
  end

  def render("user.json", %{user: user}) do
    %{id: user.id,
      lastname: user.lastname,
      firstname: user.firstname,
      username: user.username,
      email: user.email,
      team_id: user.team_id,
      role_id: user.role_id
    }
  end

  def render("jwt.json", %{jwt: jwt}) do
    %{jwt: jwt}
  end

  def render("sign_out.json", %{message: message}) do
    %{message: message}
  end

end
