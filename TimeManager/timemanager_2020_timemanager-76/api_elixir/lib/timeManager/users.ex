defmodule TimeManager.Users do
  @moduledoc """
  The Users context.
  """

  import Ecto.Query, warn: false
  alias TimeManager.Repo

  alias TimeManager.Users.User
  alias TimeManager.Guardian
  import Comeonin.Bcrypt, only: [checkpw: 2, dummy_checkpw: 0]

  @doc """
  Returns the list of users.

  ## Examples

      iex> list_users()
      [%User{}, ...]

  """
  def list_users do
    Repo.all(User)
  end

  @doc """
  Gets a single user.

  Raises `Ecto.NoResultsError` if the User does not exist.

  ## Examples

      iex> get_user!(123)
      %User{}

      iex> get_user!(456)
      ** (Ecto.NoResultsError)

  """
  def get_user!(id), do: Repo.get!(User, id)

  def get_user_per_team!(team_id) do
    User
    |> where(team_id: ^team_id)
    |> Repo.all()
  end

  @doc """
  Creates a user.

  ## Examples

      iex> create_user(%{field: value})
      {:ok, %User{}}

      iex> create_user(%{field: bad_value})
      {:error, %Ecto.Changeset{}}

  """
  def create_user(attrs \\ %{}) do
    %User{}
    |> User.changeset(attrs)
    |> Repo.insert()
  end

  @doc """
  Updates a user.

  ## Examples

      iex> update_user(user, %{field: new_value})
      {:ok, %User{}}

      iex> update_user(user, %{field: bad_value})
      {:error, %Ecto.Changeset{}}

  """
  def update_user(%User{} = user, attrs) do
    user
    |> User.changeset(attrs)
    |> Repo.update()
  end

  @doc """
  Deletes a user.

  ## Examples

      iex> delete_user(user)
      {:ok, %User{}}

      iex> delete_user(user)
      {:error, %Ecto.Changeset{}}

  """
  def delete_user(%User{} = user) do
    Repo.delete(user)
  end

  @doc """
  Returns an `%Ecto.Changeset{}` for tracking user changes.

  ## Examples

      iex> change_user(user)
      %Ecto.Changeset{data: %User{}}

  """
  def change_user(%User{} = user, attrs \\ %{}) do
    User.changeset(user, attrs)
  end

  def token_sign_in(email, password, role_id) do
    case email_password_auth_role(email, password, role_id) do
      {:ok, user} ->
        Guardian.encode_and_sign(user, %{role: user.role_id})
      _ ->
        {:error, :unauthorized}
    end
  end

  defp email_password_auth_role(email, password, role_id) when is_binary(email) and is_binary(password) do
    with {:ok, user} <- get_by_email_role(email, role_id),
         do: verify_password(password, user)
  end

  defp get_by_email_role(email, role_id) when is_binary(email) do
    query = from user in User,
                 where: user.email == ^email and user.role_id == ^role_id

    case Repo.one(query) do
      nil ->
        dummy_checkpw()
        {:error, "Login error."}
      user ->
        {:ok, user}
    end
  end

  defp verify_password(password, %User{} = user) when is_binary(password) do
    if checkpw(password, user.password_hash) do
      {:ok, user}
    else
      {:error, :invalid_password}
    end
  end
  def verify_role(role_id) do
    Repo.get_by(User, role_id: role_id)
  end


end
