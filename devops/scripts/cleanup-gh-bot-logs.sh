#!/usr/bin/env bash
set -euo pipefail

# Arguments
repository_owner="$1"
repository_name="$2"
pull_number="$3"
username="$4"
github_token="$5"

logs_url="https://api.github.com/repos/${repository_owner}/${repository_name}/issues/${pull_number}/comments"
echo "Fetching logs from: $logs_url"

# Get all logs URLs authored by the specified user
logs_urls=$(curl -sSL \
    -H "Accept: application/vnd.github+json" \
    -H "Authorization: Bearer ${github_token}" \
    "${logs_url}" \
  | jq -r --arg user "$username" '.[] | select(.user.login == $user) | .url')


# Delete logs
if [[ -z "$logs_urls" ]]; then
  echo "No logs found from user: $username"
  exit 0
fi

echo "$logs_urls" | while IFS= read -r url; do
  curl -s -X DELETE \
    -H "Accept: application/vnd.github+json" \
    -H "Authorization: Bearer ${github_token}" \
    -H "X-GitHub-Api-Version: 2022-11-28" \
    "$url"
  echo "Deleted logs: $url"
done