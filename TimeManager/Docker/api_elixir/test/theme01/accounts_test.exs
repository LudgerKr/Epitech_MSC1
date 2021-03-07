defmodule TimeManager.AccountsTest do
  use TimeManager.DataCase

  alias TimeManager.Accounts

  describe "totos" do
    alias TimeManager.Accounts.Toto

    @valid_attrs %{hashed_password: "some hashed_password", permissions: %{}, username: "some username"}
    @update_attrs %{hashed_password: "some updated hashed_password", permissions: %{}, username: "some updated username"}
    @invalid_attrs %{hashed_password: nil, permissions: nil, username: nil}

    def toto_fixture(attrs \\ %{}) do
      {:ok, toto} =
        attrs
        |> Enum.into(@valid_attrs)
        |> Accounts.create_toto()

      toto
    end

    test "list_totos/0 returns all totos" do
      toto = toto_fixture()
      assert Accounts.list_totos() == [toto]
    end

    test "get_toto!/1 returns the toto with given id" do
      toto = toto_fixture()
      assert Accounts.get_toto!(toto.id) == toto
    end

    test "create_toto/1 with valid data creates a toto" do
      assert {:ok, %Toto{} = toto} = Accounts.create_toto(@valid_attrs)
      assert toto.hashed_password == "some hashed_password"
      assert toto.permissions == %{}
      assert toto.username == "some username"
    end

    test "create_toto/1 with invalid data returns error changeset" do
      assert {:error, %Ecto.Changeset{}} = Accounts.create_toto(@invalid_attrs)
    end

    test "update_toto/2 with valid data updates the toto" do
      toto = toto_fixture()
      assert {:ok, %Toto{} = toto} = Accounts.update_toto(toto, @update_attrs)
      assert toto.hashed_password == "some updated hashed_password"
      assert toto.permissions == %{}
      assert toto.username == "some updated username"
    end

    test "update_toto/2 with invalid data returns error changeset" do
      toto = toto_fixture()
      assert {:error, %Ecto.Changeset{}} = Accounts.update_toto(toto, @invalid_attrs)
      assert toto == Accounts.get_toto!(toto.id)
    end

    test "delete_toto/1 deletes the toto" do
      toto = toto_fixture()
      assert {:ok, %Toto{}} = Accounts.delete_toto(toto)
      assert_raise Ecto.NoResultsError, fn -> Accounts.get_toto!(toto.id) end
    end

    test "change_toto/1 returns a toto changeset" do
      toto = toto_fixture()
      assert %Ecto.Changeset{} = Accounts.change_toto(toto)
    end
  end
end
