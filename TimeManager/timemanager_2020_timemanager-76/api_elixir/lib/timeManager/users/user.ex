defmodule TimeManager.Users.User do
  use Ecto.Schema
  import Ecto.Changeset

  import Comeonin.Bcrypt, only: [hashpwsalt: 1]

  schema "users" do
    field :email, :string
    field :firstname, :string
    field :lastname, :string
    field :username, :string
    field :password_hash, :string
    has_many :clocks, TimeManager.Clocks.Clock
    has_many :workingtimes, TimeManager.Workingtimes.Workingtime
    belongs_to :role, TimeManager.Roles.Role
    belongs_to :team, TimeManager.Teams.Team

    field :password, :string, virtual: true
    field :password_confirmation, :string, virtual: true

    timestamps()
  end

  @doc false
  def changeset(user, attrs) do
    user
    |> cast(attrs, [:firstname, :lastname, :username, :email, :password, :password_confirmation, :team_id, :role_id])
    |> validate_format(:email, ~r/@/) # Check that email is valid
    |> validate_length(:password, min: 3) # Check that password length is >= 3
    |> validate_confirmation(:password) # Check that password === password_confirmation
    |> unique_constraint(:email)
    |> unique_constraint(:username)
    |> put_password_hash
  end

  defp put_password_hash(changeset) do
    case changeset do
      %Ecto.Changeset{valid?: true, changes: %{password: pass}}
      ->
        put_change(changeset, :password_hash, hashpwsalt(pass))
      _ ->
        changeset
    end
  end

end
