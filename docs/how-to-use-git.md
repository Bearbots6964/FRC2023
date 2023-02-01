# How to use git

## Common commands

### Checkout
<details>
<summary>Switch to an existing branch</summary>

`git checkout [branch name]`

- Example: `git checkout bob-fix-all-the-bugs`
- The branch name will ideally be one or more readable words separated by dashes
    - If working on the branch alone, I think it is best to use a unique prefix to make it clearer whose branch it is (like `bob-` in the example)
- If you have a local copy of the branch, it will switch to that
    - If you do not have a local copy of the branch, it will first fetch the branch to create a local copy
- Any changes to this branch will be seen by you alone until you push them

</details>

<details>
<summary>Create a new branch</summary>

`git checkout -b [new branch name] [(optional) base branch name]`

- Example: `git co -b do-something-cool master`
- Example: `git co -b do-something-even-cooler`
- If you leave off the base branch name, then the branch will be based off your current branch

</details>

<br>

### Pull

<details>
<summary>Sync your local branch with its remote version</summary>

`git pull`

- This will ensure that your local branch is up to date with the current version of your branch on the [remote](#remote)
- This is best used when you are working on a branch with other people or are working on more than one computer
    - You will want to pull remote changes before you start working on your local branch, so you can prevent conflicts with the [remote](#remote) version of your branch

</details>

<details>
<summary>Sync your local project with remote</summary>

`git fetch`

- This will sync all of the [remote](#remote) branches with your local project
    - Similar to `git pull`, but it won't merge your current local branch with its [remote](#remote) version
    - Note: This will not update any of your local branches (including your current branch)
- This is useful for operations such as [rebasing](#rebase)

</details>

<br>

### Commit

<details>
<summary>List edited files</summary>

`git status`

- It will list all files edited since the last commit
- Both [tracked](#tracked-file) and [untracked](#untracked-file) files will be shown

</details>

<details>
<summary>Stage all edited (tracked and untracked) files for a commit</summary>

`git add .`

- This operation can instead be done in the Source Control panel within VSCode by hitting the `+` in the Changes tab

</details>

<details>
<summary>Stage specific file(s) for a commit</summary>

`git add [file name] [(optional) another file name] [...]`

- Example: `git add test.txt some-folder/some-other-file.java something-else.md`
- This operation can instead be done in the Source Control panel within VSCode by hitting the `+` on the line of the file you want to stage

</details>

<details>
<summary>Commit staged changes</summary>

`git commit -m "[Commit message]"`

- Example: `git commit -m "Some helpful commit message"`
- This will create a commit on your branch which includes all of the changes which you currently have staged
- It might be easier to instead use the Source Control panel within VSCode to do this

</details>

<details>
<summary>Commit all changes to tracked files</summary>

`git commit -a -m "[Commit message]"`

- Example: `git commit -a -m "Some helpful commit message"`
- This will create a commit on your branch which includes all of the changes which you currently have staged, plus any other changes to [tracked files](#tracked-file)
    - Note: Be aware that this will not include any [untracked files](#untracked-file) in the commit

</details>

<br>

### Push

<details>
<summary>Push new branch to remote</summary>

`git push -u origin HEAD`

- This command will fail if there is already a branch on [remote](#remote) which matches your current branch's name
    - In which case, you should instead use `git push`
- If you have a newer version of git (I think >= v2.38), you can push new branches using `git push`

</details>

<details>
<summary>Push changes to an existing branch</summary>

`git push`

- This command will fail if you are behind the [remote](#remote) version of your current branch
    - There are a couple ways to avoid this:
        - Make sure your branch is up-to-date by performing `git pull` before making changes to the branch
        - Try to never work on the same branch as someone else at the same time
    - One possible fix if you conflict with the [remote](#remote) version of your branch
        - [Rebase](#rebase) off the [remote](#remote) version of your branch by performing `git fetch` and then `git rebase origin/[your-branch-name]`

</details>

<br>

---

<br>

## Other commands

<details>
<summary>Rebase</summary>

`git rebase [other branch name]`

- Example: `git rebase some-other-branch`
- Example: `git rebase origin/some-remote-branch`
- This will [rebase](#rebase) your current branch off the specified branch
- Usually, it is best to first to `git fetch` to sync with the [remote](#remote) and then rebase off `origin/[branch name]`
    - This will ensure that you rebase off the most up-to-date version of the base branch
- I use this usually to make sure that my local branch is based off the current version of `main`/`master` before I create a pull request

</details>

<details>
<summary>View commit history of your branch</summary>

`git log`

- This will show the commits, in order from most-recent to least-recent, for your current branch
- Navigation:
    - Hitting `Enter` will continue showing more commits (going back in history)
    - Hitting `Q` will quit

</details>

<br>

---

<br>

## Definitions

### Tracked file
A file which already existed but was edited or moved.

### Untracked file
A file which did not previously exist and was added.

### Remote
Probably not concisely stated: The version of your project stored online.

### Behind a branch
Your branch is missing commits which are present on the other branch. This commonly happens when trying to push changes on a local branch when someone else already pushed changes to the [remote](#remote) version of your branch.

### Rebase
Make the commit from one branch come after the commits on another branch.

For example, if you have branches A and B, rebasing B on A will make it so B branches off A and therefore B would have all the commits from A followed by all the commits from B before the rebase.

Before rebase (`X -> Y` means "X is the base branch of Y"):
```
master -> A
master -> B
```

After rebasing B on A:
```
master -> A -> B
```